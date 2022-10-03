import axios from "axios";

class RestService {
    calculateCells(data) {
        return axios.post('http://176.124.192.7:8081/cells', data)
    }
}

export default new RestService()