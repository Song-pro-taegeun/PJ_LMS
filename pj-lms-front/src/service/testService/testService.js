import axios from "axios";
export const testApi = async (param) => {
  return await axios(param);
};
