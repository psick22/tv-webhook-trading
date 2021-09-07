import styled from 'styled-components';

import { Layout } from 'antd';
import { MAIN_DARK } from './colors';


const Header = styled(Layout.Header)`
  background-color: ${MAIN_DARK};
  z-index: 1;
  display: inline-block;
  padding-right: 0;

  .ant-menu-root {
    background-color: ${MAIN_DARK};
  }
`;

export default Header;