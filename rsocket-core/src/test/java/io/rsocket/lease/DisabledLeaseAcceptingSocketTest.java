/*
 * Copyright 2016 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rsocket.lease;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.util.PayloadImpl;
import org.junit.Rule;
import org.junit.Test;
import io.reactivex.subscribers.TestSubscriber;
import reactor.core.publisher.Flux;

public class DisabledLeaseAcceptingSocketTest {
    @Rule
    public final LeaseSocketRule socketRule = new LeaseSocketRule();

    @Test(timeout = 10000)
    public void testFireAndForget() throws Exception {
        TestSubscriber<Void> subscriber = TestSubscriber.create();
        socketRule.getRSocket().fireAndForget(PayloadImpl.EMPTY).subscribe(subscriber);
        subscriber.assertError(UnsupportedOperationException.class);
        socketRule.getMockSocket().assertFireAndForgetCount(1);
    }

    @Test(timeout = 10000)
    public void testRequestResponse() throws Exception {
        TestSubscriber<Payload> subscriber = TestSubscriber.create();
        socketRule.getRSocket().requestResponse(PayloadImpl.EMPTY).subscribe(subscriber);
        subscriber.assertError(UnsupportedOperationException.class);
        socketRule.getMockSocket().assertRequestResponseCount(1);
    }

    @Test(timeout = 10000)
    public void testRequestStream() throws Exception {
        TestSubscriber<Payload> subscriber = TestSubscriber.create();
        socketRule.getRSocket().requestStream(PayloadImpl.EMPTY).subscribe(subscriber);
        subscriber.assertError(UnsupportedOperationException.class);
        socketRule.getMockSocket().assertRequestStreamCount(1);
    }

    @Test(timeout = 10000)
    public void testRequestChannel() throws Exception {
        TestSubscriber<Payload> subscriber = TestSubscriber.create();
        socketRule.getRSocket().requestChannel(Flux.just(PayloadImpl.EMPTY)).subscribe(subscriber);
        subscriber.assertError(UnsupportedOperationException.class);
        socketRule.getMockSocket().assertRequestChannelCount(1);
    }

    @Test(timeout = 10000)
    public void testMetadataPush() throws Exception {
        TestSubscriber<Void> subscriber = TestSubscriber.create();
        socketRule.getRSocket().metadataPush(PayloadImpl.EMPTY).subscribe(subscriber);
        subscriber.assertError(UnsupportedOperationException.class);
        socketRule.getMockSocket().assertMetadataPushCount(1);
    }

    public static class LeaseSocketRule extends AbstractSocketRule<DisabledLeaseAcceptingSocket> {
        @Override
        protected DisabledLeaseAcceptingSocket newSocket(RSocket delegate) {
            return new DisabledLeaseAcceptingSocket(delegate);
        }
    }
}