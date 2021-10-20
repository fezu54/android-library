/* Nextcloud Android Library is available under MIT license
 *
 *   @author Tobias Kaminsky
 *   Copyright (C) 2021 Tobias Kaminsky
 *   Copyright (C) 2021 Nextcloud GmbH
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *   EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *   MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *   BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *   ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *   CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 */
package com.nextcloud.android.lib.resources.profile;

import com.google.gson.reflect.TypeToken;
import com.nextcloud.common.NextcloudClient;
import com.nextcloud.operations.GetMethod;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.common.utils.Log_OC;
import com.owncloud.android.lib.ocs.ServerResponse;
import com.owncloud.android.lib.resources.OCSRemoteOperation;

import org.apache.commons.httpclient.HttpStatus;

/**
 * Get hoverCard of an user
 */

public class GetHoverCardRemoteOperation extends OCSRemoteOperation<HoverCard> {
    private static final String TAG = GetHoverCardRemoteOperation.class.getSimpleName();
    private static final String DIRECT_ENDPOINT = "/ocs/v2.php/hovercard/v1/";
    private static final String JSON_FORMAT = "?format=json";

    private String userId;

    public GetHoverCardRemoteOperation(String userId) {
        this.userId = userId;
    }

    @Override
    public RemoteOperationResult<HoverCard> run(NextcloudClient client) {
        RemoteOperationResult<HoverCard> result;
        GetMethod getMethod = null;

        try {
            getMethod = new GetMethod(client.getBaseUri() + DIRECT_ENDPOINT + userId + JSON_FORMAT, true);

            int status = client.execute(getMethod);

            if (status == HttpStatus.SC_OK) {
                HoverCard hoverCard = getServerResponse(getMethod,
                        new TypeToken<ServerResponse<HoverCard>>() {
                        })
                        .getOcs().getData();

                result = new RemoteOperationResult<>(true, getMethod);
                result.setResultData(hoverCard);
            } else {
                result = new RemoteOperationResult<>(false, getMethod);
            }
        } catch (Exception e) {
            result = new RemoteOperationResult<>(e);
            Log_OC.e(TAG, "Get hoverCard for user " + userId + " failed: " + result.getLogMessage(),
                    result.getException());
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        return result;
    }
}
