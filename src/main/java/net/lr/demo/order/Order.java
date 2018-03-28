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

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Order {
    Logger log = LoggerFactory.getLogger(this.getClass());

    private String userId;
    private BigDecimal amount;
    private String orderId;

    public Order(String orderId, String customerId, BigDecimal amount) {
        this.orderId = orderId;
        this.userId = customerId;
        this.amount = amount;
    }
    
    public void place() {
        MDC.put("customer-id", this.userId);
        MDC.put("order-id", this.orderId);
        MDC.put("order-state", "placed");
        MDC.put("order-amount-eur", amount.toPlainString()); 
        log.info("Order placed.");
        MDC.clear();
    }
}
