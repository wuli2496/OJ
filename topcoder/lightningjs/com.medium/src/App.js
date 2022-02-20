/*
 * If not stated otherwise in this file or this component's LICENSE file the
 * following copyright and licenses apply:
 *
 * Copyright 2020 Metrological
 *
 * Licensed under the Apache License, Version 2.0 (the License);
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

import { Lightning, Utils } from '@lightningjs/sdk'

/*
export default class App extends Lightning.Component {
  static getFonts() {
    return [{ family: 'Regular', url: Utils.asset('fonts/Roboto-Regular.ttf') }]
  }

  static _template() {
    return {
      Background: {
        w: 1920,
        h: 1080,
        color: 0xfffbb03b,
        src: Utils.asset('images/background.png'),
      },
      Logo: {
        mountX: 0.5,
        mountY: 1,
        x: 960,
        y: 600,
        src: Utils.asset('images/logo.png'),
      },
      Text: {
        mount: 0.5,
        x: 960,
        y: 720,
        text: {
          text: "Let's start Building!",
          fontFace: 'Regular',
          fontSize: 64,
          textColor: 0xbbffffff,
        },
      },
    }
  }

  _init() {
    this.tag('Background')
      .animation({
        duration: 15,
        repeat: -1,
        actions: [
          {
            t: '',
            p: 'color',
            v: { 0: { v: 0xfffbb03b }, 0.5: { v: 0xfff46730 }, 0.8: { v: 0xfffbb03b } },
          },
        ],
      })
      .start()
  }
}
*/
class BasicUsageExample extends Lightning.Application {
  static _template() {
    return {
      LilLightning: {
        x: Math.floor(Math.random() * window.innerWidth),
        y: Math.floor(Math.random() * window.innerHeight),
        src: '/static/images/logo.png',
        transitions: {
          x: { duration: 1, timingFuction: 'linear' },
          y: { duration: 1, timingFuction: 'linear' },
        },
      },
    }
  }

  _init() {
    let targetX = Math.floor(Math.random() * window.innerWidth)
    let targetY = Math.floor(Math.random() * window.innerHeight)
    this.tag('LilLightning').setSmooth('x', targetX)
    this.tag('LilLightning').setSmooth('y', targetY)
    this.tag('LilLightning')
      .transition('x')
      .on('finish', () => {
        let targetX = Math.floor(Math.random() * window.innerWidth)
        this.tag('LilLightning').setSmooth('x', targetX)
      })
    this.tag('LilLightning')
      .transition('y')
      .on('finish', () => {
        let targetY = Math.floor(Math.random() * window.innerHeight)
        this.tag('LilLightning').setSmooth('y', targetY)
      })
  }
}

const App = new BasicUsageExample({
  stage: { w: window.innerWidth, h: window.innerHeight, useImageWorker: false },
})
document.body.appendChild(App.stage.getCanvas())

export default App
