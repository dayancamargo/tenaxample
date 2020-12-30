package com.tenaxample.model.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.sun.istack.NotNull;
import com.tenaxample.exception.BaseException;
import com.tenaxample.exception.model.Error;
import com.tenaxample.model.BaseModel;

import lombok.Getter;

/**
 * Class to make a http body response
 * @param <B> Body object,  it will be a 'data' element with some fields (B) or a list of errors;
 * @param <M> Meta object, it will contain a 'meta" element with some information (M);
 *
 */
@Getter
public class Response <B, M> extends BaseModel {
    private final List<B> data;
    private final List<Error> errors;
    private final Map<String, M> meta;

    public static ContentStep build(){
        return new ContentBuilder();
    }

    private Response(ContentBuilder builder){
        this.data   = builder.data;
        this.errors = builder.errors;
        this.meta   = builder.meta;
    }

    public interface ContentStep<B> {
        Build withoutContent();
        MetaStep withBody(B body);
        MetaStep withPagination(B body);
        MetaStep withErrors(Error... errors);
    }

    public interface MetaStep<M> {
        Build withMeta(@NotNull M meta);
        Response create();
    }

    public interface Build  {
        Response create();
    }

    private static class ContentBuilder<B, M> implements ContentStep<B>, MetaStep<M>, Build {
        private List<B> data;
        private List<Error> errors;
        private Map meta;

        public MetaStep withPagination(@NotNull B body){
            if(!(body instanceof Page)) {
                throw new BaseException("Not a paginated object");
            }

            Page page = ((Page) body);

            data = page.getContent();

            Pagination pagination = new Pagination(page.getNumber(),
                    page.getSize(),
                    page.getTotalElements(),
                    page.getTotalPages());

            meta = new HashMap();
            meta.put("pagination", pagination);

            return this;
        }

        public MetaStep withBody(@NotNull B body){
            data =  new ArrayList<>();

            if(body instanceof List) {
                data.addAll((List)body);
            } else {
                data.add(body);
            }

            return this;
        }

        public MetaStep withErrors(@NotNull Error... errors){
            data = null;
            this.errors = Arrays.asList(errors);
            return this;
        }

        public Build withMeta(@NotNull M meta) {
            String key = StringUtils.uncapitalize(meta.getClass().getSimpleName());

            if(Objects.isNull(this.meta)) {
                this.meta = new HashMap();
            }

            this.meta.put(key, meta);
            return this;
        }

        public Build withoutContent() {
            this.data   = null;
            this.errors = null;
            this.meta   = null;
            return  this;
        }

        public Response create() {
            return new Response(this);
        }
    }
}