import React from 'react';
import { Menu } from 'antd';
import { Link } from 'react-router-dom';
import Header from '../styles/Header';


function Navbar(props) {
  return (
    <Header>
      <Menu
        theme='dark'
        mode='horizontal'
        defaultSelectedKeys={['home']}
      >
        <Menu.Item key='home'>
          <Link to='/home'>Home</Link>
        </Menu.Item>
        <Menu.Item key='webhooks'>
          <Link to='/webhook'>웹훅</Link>
        </Menu.Item>
        <Menu.Item key='signIn' style={{ float: 'right', marginLeft: 'auto' }}>
          <Link to='/login'>로그인</Link>
        </Menu.Item>
        <Menu.Item key='signUp'>
          <Link to='/signup'>회원가입</Link>
        </Menu.Item>
        <Menu.Item key='settings'>
          <Link to='/settings'>설정</Link>
        </Menu.Item>

      </Menu>


    </Header>
  )
    ;
}

export default Navbar;