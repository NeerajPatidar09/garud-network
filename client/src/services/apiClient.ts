import axios from "axios";

import { tokenService } from "../features/auth/services/tokenService";

const apiClient = axios.create({

  baseURL: import.meta.env.VITE_API_BASE_URL,

  timeout: 15000,

  headers: {
    "Content-Type": "application/json",
  },

});

apiClient.interceptors.response.use(

  (response) => response,

  (error) => {

    if (!error.response) {

      console.error(
        "Network error or backend unavailable"
      );

    } else {

      const status = error.response.status;

      if (status === 401) {

        console.error(
          "Authentication required or token invalid"
        );

      } else if (status === 403) {

        console.error(
          "Insufficient permissions"
        );

      } else if (status === 404) {

        console.error(
          "Requested resource not found"
        );

      } else if (status >= 500) {

        console.error(
          "Server error"
        );

      }

    }

    return Promise.reject(error);

  }

);

export default apiClient;