import {reactive} from 'vue'
import { defineStore } from 'pinia'

export const userStore = defineStore('user',() => {
    const user = reactive({})

    const setUserStore = (newUser) => {
        Object.assign(user,newUser)
    }

    const removeUser = () => {
        user.value = ''
    }

    return {
        user,setUserStore,removeUser
    }
},
{
    persist:true
})
export default userStore