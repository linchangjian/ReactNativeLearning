import React from 'react';
import {View, Text, Button} from 'react-native';
import {createAppContainer, createStackNavigator, StackActions, NavigationActions} from 'react-navigation';
import StartActivity from "./util/StartActivity.js";

class HomeScreen extends React.Component {
    render() {
        return (
            <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>
                <Text>丰小哥收件列表</Text>

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