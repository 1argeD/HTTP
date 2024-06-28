# HTTP

버전 상 몽고 db의 GridFSDBFile 지원 종료되어
List<GridFSDBFile> files = gridFsTemplate.find(null);의 경우
List<GridFSFile> files = new ArrayList<>();
GridFSFindIterable file = gridFsTemplate.find(null);
로 받아야 한다.

GridFSDBFile의 함수 writeTo는 메소드는 직접 구현을 해야했다.

Spring이  remoteing.httpinvoker을 더 이상 지원하지 않아
HttpInvokerServiceExporter 등 해당 기능을 사용 할 수 없어.

원격프로 시저를 RMI프로시저로 변경하여 호출하였음.