import {Axios} from "axios";

class RestService {
    calculateCells(data) {
        return axios.post('http://minitable-backend:8081/cells', data)
    }
}

export default new RestService()