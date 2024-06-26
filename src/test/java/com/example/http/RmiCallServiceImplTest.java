package com.example.http;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import static org.assertj.core.api.Assertions.assertThat;

public class RmiCallServiceImplTest  {
    Log log = LogFactory.getLog(RmiCallServiceImplTest.class);

    public String[] getConfigLocations() {
        return new String[] {"classpath:actions/action-config.xml" , "classpath:services/serviceContext-sample.xml"};
    }

    private RemoteController remoteController;

    public void setRemoteController(RemoteController remoteController) {
        this.remoteController = remoteController;
    }
    @Test
    public void testCall() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.setParameter("keyword", "나이키");
        String result = remoteController.search(request, response);
        assertThat("나이키").isEqualTo(result);
    }
}
