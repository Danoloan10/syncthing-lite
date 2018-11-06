/*
 * Copyright (C) 2018 Jonas Lochmann
 *
 * This Java file is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.syncthing.java.discovery

import net.syncthing.java.core.beans.DeviceId

class DevicesAddressesManager {
    private val data = mutableMapOf<DeviceId, DeviceAddressesManager>()

    fun getDeviceAddressManager(deviceId: DeviceId) = synchronized(data) {
        val item = data[deviceId]

        if (item != null) {
            item
        } else {
            val newItem = DeviceAddressesManager(deviceId)

            data[deviceId] = newItem

            newItem
        }
    }
}