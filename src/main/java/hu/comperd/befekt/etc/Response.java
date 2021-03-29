package hu.comperd.befekt.etc;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Response<T> {

    private List<Error> errors;
    private T           data;

    public Response() {
    }

    public Response(final T data) {
        this.data = data;
    }

    public Response(final List<Error> errors, final T data) {
        this.errors = errors;
        this.data = data;
    }

    public Response(final Error error, final T data) {
        if (errors == null) {
            this.errors = new ArrayList<>(1);
        }
        errors.add(error);
        this.data = data;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(final List<Error> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }

}
