package hu.comperd.befekt.controller;

import java.sql.SQLException;

import javax.xml.bind.ValidationException;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import hu.comperd.befekt.etc.FunctionE;

@Controller
public class DefaultTransactionalController implements TransactionalController {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T> T processInTransaction(final FunctionE<Object, T> function) throws SQLException, ValidationException {
        return function.apply(null);
    }
}
