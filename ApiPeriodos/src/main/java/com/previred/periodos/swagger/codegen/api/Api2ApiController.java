package com.previred.periodos.swagger.codegen.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.previred.periodos.servicio.PeriodosCompletosService;
import com.previred.periodos.swagger.codegen.model.PeriodoCompleto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class Api2ApiController implements Api2Api {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private PeriodosCompletosService periodosCompletosService;

    @org.springframework.beans.factory.annotation.Autowired
    public Api2ApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<PeriodoCompleto> periodoscompletos() {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                	PeriodoCompleto detalle = periodosCompletosService.getPeriodos();
                    ResponseEntity<PeriodoCompleto> respuesta = new ResponseEntity<>(detalle, HttpStatus.OK);
                    return respuesta;
                } catch (Exception e) {
                    log.error("Couldn't serialize response for content type application/xml", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default DefaultApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
