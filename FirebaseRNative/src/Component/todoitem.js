import React from 'react';
import {StyleSheet,Text, TouchableOpacity} from 'react-native';

export default function TodoItem({ item, pressHandler }){

return(
    <TouchableOpacity onPress={()=> pressHandler(item.key)}>
        <Text style={styles.item}>
        {item.text}
        </Text>
    </TouchableOpacity>
)
}

const styles = StyleSheet.create({
    item:{
        paddingTop: 16,
        marginTop: 16,
        borderColor: '#bbb',
        borderWidth: 1,
        borderStyle: 'dashed',
        borderRadius: 10
    }
});