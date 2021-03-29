package hu.comperd.befekt.controller;

import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hu.comperd.befekt.etc.FunctionE;
import hu.comperd.befekt.etc.Response;

public abstract class BaseController {

    private static final Logger   LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private TransactionalController transactionalController;

    public <T> ResponseEntity<Response<T>> processRequest(final FunctionE<Object, T> function) {
        try {
            final T result = transactionalController.processInTransaction(function);
            return ResponseEntity.ok(new Response<>(result));
        } catch (final ValidationException e) {
            LOGGER.info("ValidationException in processRequest(): {}", e.getMessage());
            return new ResponseEntity<>(new Response<>(new Error(e.getMessage()), null), HttpStatus.BAD_REQUEST);
        } catch (final Exception t) {
            LOGGER.error("Exception in processRequest()", t);
            return new ResponseEntity<>(new Response<>(new Error(t.getMessage()), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
        	//
        }
    }
}
