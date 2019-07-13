import React from 'react';
import {View, Text, Button} from 'react-native';
import {createAppContainer, createStackNavigator, StackActions, NavigationActions} from 'react-navigation';
import StartActivity from "./util/StartActivity.js";

class HomeScreen extends React.Component {
    render() {
        return (
            <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
                <Text>Home Screen</Text>
                <Button
                    title="我是数字化平台"
                    onPress={() => {
                        this.props.navigation.dispatch(StackActions.reset({
                            index: 0,
                            actions: [
                                NavigationActions.navigate({routeName: 'Details'})
                            ],
                        }))
                    }}
                />
                <Button
                    title="启动一个容器，跳转到丰小哥页面"
                    onPress={() => {
                        // this.props.navigation.dispatch(StackActions.reset({
                        //     index: 0,
                        //     actions: [
                        //         NavigationActions.navigate({routeName: 'Linchangjian'})
                        //     ],
                        // }))
                        // this.props.navigation.push('Linchangjian');
                        StartActivity.startNewPage('fengxiaoge',"");
                    }}
                />
                <Button
                    title="启动一个容器，跳转到丰小代页面"
                    onPress={() => {
                        // this.props.navigation.dispatch(StackActions.reset({
                        //     index: 0,
                        //     actions: [
                        //         NavigationActions.navigate({routeName: 'Linchangjian'})
                        //     ],
                        // }))
                        // this.props.navigation.push('Linchangjian');
                        StartActivity.startNewPage('fengxiaodai',"");
                    }}
                />
                <Button
                    title="收件列表"
                    onPress={() => {
                        // this.props.navigation.dispatch(StackActions.reset({
                        //     index: 0,
                        //     actions: [
                        //         NavigationActions.navigate({routeName: 'Linchangjian'})
                        //     ],
                        // }))
                        // this.props.navigation.push('Linchangjian');
                        StartActivity.startNewPage('fengxiaoge',"pickUp");
                    }}
                />
            </View>
        );
    }
}

class DetailsScreen extends React.Component {
    render() {
        return (
            <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
                <Text>Details Screen</Text>
            </View>
        );
    }
}

class LogoTitle extends React.Component{
    render(){
        return(<View>
            <Button title={'LCJButton'} />
        </View>);
    }
}

class LinchangjianPage extends React.Component {
    static navigationOptions = {
      title:'linchangjian',
        headerStyle:{
          backgroundColor:'#f4511e',
        },
        headerTintColor:'#fff',
        headerTitleStyle:{
          fontWeight: 'bold',
        },
        headerTitle: <LogoTitle/>,
        headerRight: (<Button
        onPress={()=>{
            alert('This is a lcj button')
        }}
        title="Info"
        color='#B440F4'
        />),
    };

    render(){
        return(<View style={{flex:1,alignItems:'center',justifyContent:'center'}} >
            <Button title={"linchangjian page"} onPress={()=>{
                // this.props.navigation.dispatch(StackActions.reset({
                //     index: 0,
                //     actions: [
                //         NavigationActions.navigate({routeName: 'Home'})
                //     ],
                // }))
                this.props.navigation.pop();
            }}/>
        </View>);
    }
}


const AppNavigator = createStackNavigator({
    Home: {
        screen: HomeScreen,
    },
    Details: {
        screen: DetailsScreen,
    },
    Linchangjian : {
        screen:LinchangjianPage,
    },
}, {
    initialRouteName: 'Home',
});

export default createAppContainer(AppNavigator);