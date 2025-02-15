/** <pre>
Copyright 2016 ResearchSpace

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
</pre> */
package com.researchspace.springrest.ext;


import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Logs a 400 response instead of throwing exception, so we can capture the error message from Json
 * @author rspace
 * @see http://springinpractice.com/2013/10/07/handling-json-error-object-responses-with-springs-resttemplate/
 */
@Slf4j
public class LoggingResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        log.error("Response error: {} {}", response.getStatusCode(), response.getStatusText());
    }

    @Override
    public boolean hasError(final ClientHttpResponse response) throws IOException {
        return RestUtil.isError(response.getStatusCode());
    }
}
