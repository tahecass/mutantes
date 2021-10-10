package co.com.mutant.model;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-10-04T13:02:05.655-05:00")

public class ErrorDetail {
    @JsonProperty("code")
    private String code = null;

    @JsonProperty("detail")
    private String detail = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("source")
    private String source = null;

    @JsonProperty("status")
    private String status = null;

    @JsonProperty("title")
    private String title = null;

    @JsonProperty("meta")
    private Meta meta;

    public ErrorDetail code(String code) {
        this.code = code;
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ErrorDetail detail(String detail) {
        this.detail = detail;
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ErrorDetail id(String id) {
        this.id = id;
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ErrorDetail source(String source) {
        this.source = source;
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ErrorDetail status(String status) {
        this.status = status;
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorDetail title(String title) {
        this.title = title;
        return this;
    }

    @ApiModelProperty(required = true, value = "")
    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
