import axios from "axios";

class RestService {
    calculateCells(data) {
        return axios.post('http://37.143.9.23:8081/cells', data)
    }
}

export default new RestService()