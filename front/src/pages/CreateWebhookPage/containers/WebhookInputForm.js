import React, { useState } from 'react';
import { Button, Col, Form, Input, InputNumber, Radio, Row, Slider } from 'antd';

const formItemLayout = {
  labelCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 8,
    },
  },
  wrapperCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 16,
    },
  },
};

function WebhookInputForm({ handleFinish }) {
  const [inputValue, setInputValue] = useState(0);

  const handleChange = (value) => {
    console.log(value);

    if (isNaN(value)) {
      console.log('nan');
      return;
    }

    setInputValue(value);
  };

  return (
    <Form {...formItemLayout} style={{ marginTop: '1rem' }} onFinish={handleFinish}>
      <Row>
        <Col span={12}>
          <Form.Item
            name={'username'}
            label={'username'}
            rules={[{ required: true, message: 'username은 필수 조건입니다' }]}
          >
            <Input />
          </Form.Item>
        </Col>
      </Row>
      <Row>
        <Col span={12}>
          <Form.Item
            name={'type'}
            label={'웹훅 타입'}
            rules={[{ required: true, message: '웹훅 타입은 필수 조건입니다' }]}
          >
            <Radio.Group>
              <Radio.Button value={'NORMAL'}>일반</Radio.Button>
              <Radio.Button value={'STRATEGY'}>전략</Radio.Button>
            </Radio.Group>
          </Form.Item>
        </Col>
      </Row>
      <Row>
        <Col span={12}>
          <Form.Item name={'strategyName'} label={'전략 이름'} rules={[{ required: false }]}>
            <Input placeholder={'전략 타입이라면 구분할 수 있는 전략 이름을 입력하세요'} />
          </Form.Item>
        </Col>
      </Row>
      <Row>
        <Col span={12}>
          <Form.Item
            name={'bidRate'}
            label={'한번에 매수할 비율'}
            rules={[{ required: false, message: '매도할 비율은 필수 조건입니다' }]}
          >
            <Slider min={0} max={1} step={0.01} />
          </Form.Item>
        </Col>
      </Row>
      <Row>
        <Col span={12}>
          <Form.Item
            name={'askRate'}
            label={'한번에 매도할 비율'}
            rules={[{ required: false, message: '매도할 비율은 필수 조건입니다' }]}
          >
            <Slider min={0} max={1} step={0.01} />
          </Form.Item>
        </Col>
      </Row>

      <Form.Item wrapperCol={{ ...formItemLayout.wrapperCol, offset: 8 }}>
        <Button type="primary" htmlType="submit">
          Submit
        </Button>
      </Form.Item>
    </Form>
  );
}

export default WebhookInputForm;
