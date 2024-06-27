package com.example.http;

import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = "/files")
public class FileController {

    private GridFsTemplate gridFsTemplate;

    private static Query getFilenameQuery(String name) {
        return Query.query(GridFsCriteria.whereFilename().is(name));
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createOrUpdate(@RequestParam MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();

        maybeLoadFile(name).ifPresent(
                p -> gridFsTemplate.delete(getFilenameQuery(name))
        );

        gridFsTemplate.store(file.getInputStream(), name, file.getContentType());
        return "redirect:/";
    }

    @GetMapping
    @ResponseBody
    List<String> list() {
        return getFile().stream()
                .map(GridFSFile::getFilename)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{name:.+}")
    ResponseEntity<?> get(@PathVariable String name) throws Exception {
        Optional<GridFSFile> optionalCreated = maybeLoadFile(name);
        if (optionalCreated.isPresent()) {
            GridFSFile created = optionalCreated.get();
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                writeTo(os);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, created.getClass().getTypeName());
                return new ResponseEntity<>(os.toByteArray(), headers, HttpStatus.OK);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public long writeTo(OutputStream file) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(String.valueOf(file));
            return writeTo(out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private List<GridFSFile> getFile() {
        List<GridFSFile> files = new ArrayList<>();
        GridFSFindIterable file = gridFsTemplate.find(null);
        file.forEach(files::add);
        return files;
    }

    private Optional<GridFSFile> maybeLoadFile(String name) {
        GridFSFile file = gridFsTemplate.findOne(getFilenameQuery(name));
        return Optional.ofNullable(file);
    }

}
