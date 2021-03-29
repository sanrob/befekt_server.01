package hu.comperd.befekt.controller;

import java.sql.SQLException;

import javax.xml.bind.ValidationException;

import hu.comperd.befekt.etc.FunctionE;

public interface TransactionalController {

    <T> T processInTransaction(final FunctionE<Object, T> function) throws SQLException, ValidationException;
}
