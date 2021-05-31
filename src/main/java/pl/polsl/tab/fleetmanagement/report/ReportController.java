package pl.polsl.tab.fleetmanagement.report;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(path = "/report")
public class ReportController {

    @GetMapping
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public void getPdf(HttpServletResponse response){
        try{
           Report report = new Report(response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
