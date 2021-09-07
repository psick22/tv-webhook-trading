import React from 'react';
import { Layout } from 'antd';
import { Route, Switch } from 'react-router-dom';
import HomePage from './HomePage/HomePage';
import Navbar from '../components/global/Navbar';
const { Content, Footer } = Layout;


function Main(props) {
  return (
    <Layout style={{ height: '100vh' }}>
      <Navbar />
      <Content style={{ padding: '1rem', minHeight: 280, overflow: 'auto' }}>
        <Layout style={{
          backgroundColor: '#fff',
          height: '100rem',
        }}>
          <Switch>
            <Route path='/home' component={HomePage} />
          </Switch>
        </Layout>
      </Content>

      <Footer style={{ textAlign: 'center' }}>
        © 2021 Crypto Bot
      </Footer>


    </Layout>
  );
}

export default Main;