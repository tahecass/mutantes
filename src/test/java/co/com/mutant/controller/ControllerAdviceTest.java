package co.com.mutant.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import co.com.mutant.model.ErrorDetail;
import co.com.mutant.model.Errors;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.xnio.OptionMap;
import org.xnio.StreamConnection;
import org.xnio.XnioIoThread;
import org.xnio.conduits.ConduitStreamSinkChannel;
import org.xnio.conduits.ConduitStreamSourceChannel;
import org.xnio.conduits.StreamSinkConduit;
import org.xnio.conduits.StreamSourceConduit;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.ServerConnection;
import io.undertow.server.protocol.http.HttpServerConnection;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;
import io.undertow.util.Protocols;

@RunWith(MockitoJUnitRunner.class)
public class ControllerAdviceTest {

    @InjectMocks
    ControllerAdvice injectMock;
    @Mock
    private HttpHeaders httpHeaders;
    @Mock
    private HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException;
    @Mock
    private HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException;

    @Test
    public void handleMethodArgumentNotValidExceptionTest() {
        //Arrange
        HttpServletRequestImpl httpServletRequest = new HttpServletRequestImpl(createHttpExchange(), null);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        ResponseEntity<Errors> errorResponseEntity = createResponseErrorBusiness();
        List<FieldError> fieldErrors = new ArrayList<>();
        FieldError fieldError = new FieldError("objectName", "field1", "Error message");
        fieldErrors.add(fieldError);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
        MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(null, bindingResult);

        //Act
        ResponseEntity<Errors> responseEntity = injectMock.handleMethodArgumentNotValidException(httpServletRequest, methodArgumentNotValidException);

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity).isNotNull();
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0));
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getSource());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getCode());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getStatus());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getDetail());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getId());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getTitle());
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getCode(), "400");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getStatus(), "400");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getDetail(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getSource(), "/api/v1");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getTitle(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @Test
    public void handleHttpMediaTypeNotSupportedExceptionTest() {
        HttpServletRequestImpl httpServletRequest = new HttpServletRequestImpl(createHttpExchange(), null);
        ResponseEntity<Errors> errorResponseEntity = createResponseErrorBusiness();
        when(httpMediaTypeNotSupportedException.getMessage()).thenReturn("Bad Request");

        //Act
        ResponseEntity<Errors> responseEntity = injectMock.handleHttpMediaTypeNotSupportedException(httpMediaTypeNotSupportedException, httpServletRequest);

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity).isNotNull();
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0));
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getSource());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getCode());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getStatus());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getDetail());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getId());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getTitle());
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getCode(), "400");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getStatus(), "400");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getDetail(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getSource(), "/api/v1");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getTitle(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @Test
    public void handleHttpMessageNotReadableExceptionTest() {
        HttpServletRequestImpl httpServletRequest = new HttpServletRequestImpl(createHttpExchange(), null);
        ResponseEntity<Errors> errorResponseEntity = createResponseErrorBusiness();

        //Act
        ResponseEntity<Errors> responseEntity = injectMock.handleHttpMessageNotReadableException(httpServletRequest, null);

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity).isNotNull();
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0));
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getSource());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getCode());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getStatus());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getDetail());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getId());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getTitle());
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getCode(), "400");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getStatus(), "400");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getDetail(), "have an actual syntax error in your JSON");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getSource(), "/api/v1");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getTitle(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @Test
    public void handleHttpRequestMethodNotSupportedExceptionTest() {
        HttpServletRequestImpl httpServletRequest = new HttpServletRequestImpl(createHttpExchange(), null);
        ResponseEntity<Errors> errorResponseEntity = createResponseErrorBusiness();
        when(httpRequestMethodNotSupportedException.getMethod()).thenReturn("GET");

        //Act
        ResponseEntity<Errors> responseEntity = injectMock.handleHttpRequestMethodNotSupportedException(httpServletRequest, httpRequestMethodNotSupportedException);

        //Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity).isNotNull();
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0));
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getSource());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getCode());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getStatus());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getDetail());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getId());
        Assert.assertNotNull(responseEntity.getBody().getErrors().get(0).getTitle());
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getCode(), "400");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getStatus(), "400");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getDetail(), "Method GET not supported");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getSource(), "/api/v1");
        Assert.assertEquals(responseEntity.getBody().getErrors().get(0).getTitle(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }



    public static HttpServerExchange createHttpExchange() {
        final HeaderMap headerMap = new HeaderMap();
        final StreamConnection streamConnection = createStreamConnection();
        final OptionMap options = OptionMap.EMPTY;
        final ServerConnection connection = new HttpServerConnection(streamConnection, null, null, options, 0, null);
        return createHttpExchange(connection, headerMap);
    }

    private static StreamConnection createStreamConnection() {
        final StreamConnection streamConnection = Mockito.mock(StreamConnection.class);
        ConduitStreamSinkChannel sinkChannel = createSinkChannel();
        Mockito.when(streamConnection.getSinkChannel()).thenReturn(sinkChannel);
        ConduitStreamSourceChannel sourceChannel = createSourceChannel();
        Mockito.when(streamConnection.getSourceChannel()).thenReturn(sourceChannel);
        XnioIoThread ioThread = Mockito.mock(XnioIoThread.class);
        return streamConnection;
    }

    private static ConduitStreamSinkChannel createSinkChannel() {
        StreamSinkConduit sinkConduit = Mockito.mock(StreamSinkConduit.class);
        ConduitStreamSinkChannel sinkChannel = new ConduitStreamSinkChannel(null, sinkConduit);
        return sinkChannel;
    }

    private static ConduitStreamSourceChannel createSourceChannel() {
        StreamSourceConduit sourceConduit = Mockito.mock(StreamSourceConduit.class);
        ConduitStreamSourceChannel sourceChannel = new ConduitStreamSourceChannel(null, sourceConduit);
        return sourceChannel;
    }

    private static HttpServerExchange createHttpExchange(ServerConnection connection, HeaderMap headerMap) {
        HttpServerExchange httpServerExchange = new HttpServerExchange(connection, null, headerMap, 200);
        httpServerExchange.setRequestMethod(new HttpString("GET"));
        httpServerExchange.setProtocol(Protocols.HTTP_1_1);
        httpServerExchange.setRequestURI("/api/v1");
        return httpServerExchange;
    }

    public ResponseEntity<Errors> createResponseErrorBusiness() {
        Errors errors = new Errors();
        List<ErrorDetail> listErrorDatail = new ArrayList<>();
        listErrorDatail.add(createErrorDetail());
        errors.setErrors(listErrorDatail);
        return new ResponseEntity<>( errors, httpHeaders, HttpStatus.FAILED_DEPENDENCY);
    }

    public ErrorDetail createErrorDetail() {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setCode("400");
        errorDetail.setDetail(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorDetail.setId("1");
        errorDetail.setStatus("400");
        errorDetail.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorDetail.setSource("/api/v1");
        return errorDetail;
    }
}