/** @format */

import {AppRegistry} from 'react-native';
import {name as appName} from './app.json';
import App2 from './js/App2'
import pickUp2 from './js/pickUp'

AppRegistry.registerComponent(appName, () => App2);
AppRegistry.registerComponent('pickUp', () => pickUp2);
