/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.common.protocol.body;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;
import org.junit.Test;

public class LockBatchRequestBodyTest {

    @Test
    public void testFromJson() throws Exception {
    	LockBatchRequestBody lbrq = new LockBatchRequestBody();
    	lbrq.setClientId("001");
    	lbrq.setConsumerGroup("002");
    	
    	Set<MessageQueue> mqSet = new HashSet<MessageQueue>();
    	MessageQueue mq = new MessageQueue("003","004",1);
    	mqSet.add(mq);
		lbrq.setMqSet(mqSet);
       
		String json = RemotingSerializable.toJson(lbrq, true);
       
		LockBatchRequestBody fromJson = RemotingSerializable.fromJson(json, LockBatchRequestBody.class);
       
		assertThat(fromJson.getClientId()).isEqualTo("001");
        assertThat(fromJson.getConsumerGroup()).isEqualTo("002");
        assertThat(fromJson.getMqSet().size()).isLessThanOrEqualTo(1);
        assertThat((new ArrayList<MessageQueue>(fromJson.getMqSet())).get(0).getTopic()).isEqualTo("003");
        assertThat((new ArrayList<MessageQueue>(fromJson.getMqSet())).get(0).getBrokerName()).isEqualTo("004");
        assertThat((new ArrayList<MessageQueue>(fromJson.getMqSet())).get(0).getQueueId()).isLessThanOrEqualTo(1);
        
    }
	
}
