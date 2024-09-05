import { useState } from "react";
import { testApi } from "../../service/testService/testService.js";

export default function TestComponent() {
  const [memberData, setMemberData] = useState();

  // api test
  const apiTest = async () => {
    const param = {
      url: "/api/auth/test",
      method: "GET",
      header: null,
      data: null,
    };
    const response = await testApi(param);
    if (response.status === 200) {
      setMemberData(response.data);
    }
  };

  const handleClick = () => {
    apiTest();
  };

  return (
    <div>
      <br />
      <button onClick={handleClick}>데이터 불러오기 js </button>
      <h2>TEST</h2>

      {memberData?.map((data, index) => (
        <div key={index}>{data.pmName}</div>
      ))}

      <br />
    </div>
  );
}
