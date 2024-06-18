package modelprovider.service.api;

import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("service")
public interface ServiceRest {

    /**
     * 项目入口
     * @param serviceRequestMsg
     * @return
     */
    @POST
    @Path("doInvoke")
    @Produces({"application/json; charset=UTF-8"})
    @Consumes({"application/json"})
    ServiceResponseMsg doInvoke(ServiceRequestMsg serviceRequestMsg);
}
