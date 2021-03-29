package hu.comperd.befekt.etc;

import java.sql.SQLException;

import javax.xml.bind.ValidationException;

@FunctionalInterface
public interface FunctionE<T, R> {

    R apply(T t) throws SQLException, ValidationException;
}
