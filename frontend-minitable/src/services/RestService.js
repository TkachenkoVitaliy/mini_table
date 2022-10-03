import {Axios} from "axios";

class RestService {
    calculateCells(data) {
        return axios.post('http://localhost:8081/cells', data)
    }
}

export default new RestService()