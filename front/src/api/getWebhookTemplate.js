import axios from 'axios';

const BASE_URL = 'http://localhost:80';
export const getWebhookTemplate = async (input) => {

  const { type, username, strategyName, bidAmount, askRate } = input;

  const params = {
    type,
    username,
    strategyName,
    bidAmount,
    askRate,
  };

  try {
    let axiosResponse = await axios.get(`${BASE_URL}/webhook`, { params: params });

    return axiosResponse;

  } catch (error) {

    console.log(error);
    return error;
  }


};
