/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package net.lr.demo.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Delivery {
    Logger log = LoggerFactory.getLogger(this.getClass());
    enum States {sent, delivered, lost};
    
    private String orderId;
    private String address;
    private String deliveryId;

    public Delivery(String deliveryId, String orderId, String address) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.address = address;
    }
    
    public void send() {
        state(States.sent);
    }

    public void deliver() {
        state(States.delivered);
    }

    public void lost() {
        state(States.lost);
    }

    private void state(States state) {
        addMetadata();
        MDC.put("delivery-state", state.name());
        if (state == States.lost) {
            log.error("Delivery {}>!", state.name());
        } else {
            log.info("Delivery {}.", state.name());
        }
        MDC.clear();
    }

    private void addMetadata() {
        MDC.put("delivery-id", this.deliveryId);
        MDC.put("order-id", this.orderId);
        MDC.put("address", this.address);
    }
}
