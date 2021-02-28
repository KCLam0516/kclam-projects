import React,{ useState } from 'react';
import {StyleSheet,Text,View,FlatList} from 'react-native';
import Header from './Component/header';
import TodoItem from './Component/todoitem';
import AddTodo from './Component/addTodo';

export default function Profile (){
    const [todos, setTodos] = useState([
        { text: 'fill on top', key: '1' },
        { text: 'click add todo', key: '2' },
        { text: 'click me to delete', key: '3' }
      ]);

      const pressHandler = (key) => {
          setTodos((prevTodos) =>{
              return prevTodos.filter(todo => todo.key != key)
          });
      }

      const submitHandler = (text) => {
          setTodos((prevTodos)=>{
            return [
                {text: text, key: Math.random().toString() },
                ...prevTodos
            ];
          })
      }

    return (
        <View style={styles.container}>
            <Header/>
            <View style={styles.content}>
                <AddTodo submitHandler={submitHandler} />
                <View style={styles.list}>
                    <FlatList
                    data={todos}
                    renderItem={({ item })=> (
                        <TodoItem item={item} pressHandler={pressHandler} />
                    )}
                     />
                </View>
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
    },
    content: {
        padding:40,
    },
    list: {
        marginTop:20
    }
});

