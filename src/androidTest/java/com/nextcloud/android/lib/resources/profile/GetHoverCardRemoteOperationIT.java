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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import com.owncloud.android.AbstractIT;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.resources.status.GetStatusRemoteOperation;
import com.owncloud.android.lib.resources.status.NextcloudVersion;
import com.owncloud.android.lib.resources.status.OwnCloudVersion;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GetHoverCardRemoteOperationIT extends AbstractIT {
    @Before
    public void before() {
        RemoteOperationResult result = new GetStatusRemoteOperation(context).execute(client);
        assertTrue(result.isSuccess());

        ArrayList<Object> data = (ArrayList<Object>) result.getData();
        OwnCloudVersion ownCloudVersion = (OwnCloudVersion) data.get(0);

        assumeTrue(ownCloudVersion.isNewerOrEqual(NextcloudVersion.Companion.getNextcloud_23()));
    }

    @Test
    public void testHoverCard() {
        RemoteOperationResult<HoverCard> result = new GetHoverCardRemoteOperation(nextcloudClient.getUserId())
                .execute(nextcloudClient);
        assertTrue(result.isSuccess());

        HoverCard hoverCard = result.getResultData();

        assertEquals(nextcloudClient.getUserId(), hoverCard.getUserId());
    }
}
