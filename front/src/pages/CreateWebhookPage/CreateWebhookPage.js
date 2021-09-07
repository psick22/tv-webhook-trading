import React, { useState } from 'react';
import WebhookInputForm from './containers/WebhookInputForm';
import { Divider } from 'antd';
import TemplateBox from './components/TemplateBox';
import { getWebhookTemplate } from '../../api/getWebhookTemplate';

function CreateWebhookPage(props) {
  const [template, setTemplate] = useState('');

  const handleFinish = async (values) => {
    console.log(values);

    let { data } = await getWebhookTemplate(values);

    setTemplate(data);

  };

  return (
    <div>
      <WebhookInputForm handleFinish={handleFinish} />
      <Divider />
      <TemplateBox json={template} />
    </div>
  );
}

export default CreateWebhookPage;