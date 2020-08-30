package gomspace.second.round.gridbot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gomspace.second.round.gridbot.service.SimulationService;
import gomspace.second.round.gridbot.utils.Snapshot;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Simulation")
@RestController
@RequestMapping(value = "/api/simulation")
public class SimulationController {
	
	@Autowired
    private SimulationService simulationService; 
	
	
	@GetMapping(value = "/reset")
	@ApiOperation(value = "resetSimulation", notes = "reset the simulation", nickname = "reset")
	public void resetSimulation() {
		simulationService.initialize();
	}
	
	@PostMapping(value = "/after/number/of/iterations")
	@ApiOperation(value = "runSimulation", notes = "returns the grid after end of simulation", nickname = "simulation after n iterations")
	public ResponseEntity<String> runSimulation(@RequestBody final int number) {
		simulationService.initialize();
		String result = simulationService.SimulationAfter(number);
		simulationService.initialize();
		return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename= SimulationAfter" + number + "Moves.txt")
                // Content-Type
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                // Contet-Length
                .contentLength(result.length()) //
                .body(result);
	}
	
	@GetMapping(value = "/nextFrame", produces = "application/json")
	@ApiOperation(value = "nextFrame", notes = "continue simulation and get the next frame", nickname = "nextFrame")
	public Snapshot nextFrame() {
		return simulationService.nextFrame();
	}
	
	
	@GetMapping(value = "/nextFrameByStep", produces = "application/json")
	@ApiOperation(value = "nextStep", notes = "continue simulation and get the next frame one step at a time", nickname = "nextStep")
	public Snapshot nextStep() {
		return simulationService.nextFrameByStep();
	}


    
	
	
	
}
