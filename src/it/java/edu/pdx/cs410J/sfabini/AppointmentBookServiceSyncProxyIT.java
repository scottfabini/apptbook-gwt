package edu.pdx.cs410J.sfabini;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import edu.pdx.cs410J.web.HttpRequestHelper;
import edu.pdx.cs410J.sfabini.client.AppointmentBook;
import edu.pdx.cs410J.sfabini.client.AppointmentBookService;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class AppointmentBookServiceSyncProxyIT extends HttpRequestHelper {

  private final int httpPort = Integer.getInteger("http.port", 8080);
  private String webAppUrl = "http://localhost:" + httpPort + "/apptbook";

  @Test
  public void gwtWebApplicationIsRunning() throws IOException {
    Response response = get(this.webAppUrl);
    assertEquals(200, response.getCode());
  }

  @Test
  public void canInvokeAppointmentBookServiceWithGwtSyncProxy() {
    String moduleName = "apptbook";
    SyncProxy.setBaseURL(this.webAppUrl + "/" + moduleName + "/");

    AppointmentBookService service = SyncProxy.createSync(AppointmentBookService.class);
    int numberOfAppointments = 5;
    AppointmentBook apptbook = service.createAppointmentBook(numberOfAppointments);
    assertEquals("My Owner", apptbook.getOwnerName());
    assertEquals(numberOfAppointments, apptbook.getAppointments().size());
  }

}
