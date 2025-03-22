import Axios from "axios";
import { env } from "@/config";

export const api = Axios.create({
  baseURL: env.API_URL,
});

api.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    return Promise.reject(error);
  },
);
