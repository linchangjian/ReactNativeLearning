_retrieveData = async () => {
    try {
        const value = await AsyncStorage.getItem('@MySuperStore:key');
        if (value !== null) {
            // We have data!!
            console.log(value);
        }
    } catch (error) {
        // Error retrieving data
    }
}