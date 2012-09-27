/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.vandut.agh.magisterka.logic.service;

import javax.jws.WebService;
import javax.xml.ws.Holder;

@WebService(serviceName = "LogicService",
			targetNamespace = "http://service.logic.magisterka.agh.vandut.net/",
			endpointInterface = "net.vandut.agh.magisterka.logic.service.Logic")
public class LogicImpl implements Logic {

    public void getLogic(Holder<String> personId, Holder<String> ssn, Holder<String> name)
    {
    	personId.value = "[mod] personId.value";
        name.value = "Guillaume";
        ssn.value = "000-000-0000";
    }

}
