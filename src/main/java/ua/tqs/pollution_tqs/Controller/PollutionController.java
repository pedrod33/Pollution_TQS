package ua.tqs.pollution_tqs.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.pollution_tqs.Model.PollutionData;
import ua.tqs.pollution_tqs.Service.PollutionService;



@CrossOrigin("*")
@RestController
@RequestMapping("/api/pollution/")
public class PollutionController {

    @Autowired
    private PollutionService polService;

    @GetMapping(path = "current")
    public ResponseEntity<PollutionData> getCurrentPollution(@RequestParam double lat, @RequestParam double lng){
        PollutionData data = this.polService.getCurrentPollution(lat, lng);
        if(data==null){
            return new ResponseEntity<>(new PollutionData(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

}
