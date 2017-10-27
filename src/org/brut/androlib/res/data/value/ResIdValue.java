/**
 *  Copyright (C) 2017 Ryszard Wiśniewski <org.brut.alll@gmail.com>
 *  Copyright (C) 2017 Connor Tumbleson <connor.tumbleson@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.brut.androlib.res.data.value;

import org.brut.androlib.AndrolibException;
import org.brut.androlib.res.data.ResResource;
import org.brut.androlib.res.xml.ResValuesXmlSerializable;
import java.io.IOException;
import org.xmlpull.v1.XmlSerializer;

/**
 * @author Ryszard Wiśniewski <org.brut.alll@gmail.com>
 */
public class ResIdValue extends ResValue implements ResValuesXmlSerializable {
    @Override
    public void serializeToResValuesXml(XmlSerializer serializer,
                                        ResResource res) throws IOException, AndrolibException {
        serializer.startTag(null, "item");
        serializer
                .attribute(null, "type", res.getResSpec().getType().getName());
        serializer.attribute(null, "name", res.getResSpec().getName());
        serializer.endTag(null, "item");
    }
}
