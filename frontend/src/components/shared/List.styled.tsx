import styled from 'styled-components';
export const StyledList = styled.div`
  color: ${(props) => props.theme.colors.text};
  .MuiListItem-padding {
    padding-top: 0;
    padding-bottom: 0;
  }
  .MuiListItemText-root {
    margin: 1px;
    padding-top: 0;
    padding-bottom: 0;
    line-height: 1px;
  }
  .MuiListItemText-dense {
    padding-top: 0;
    line-height: 1px;
  }
  display: flex;
  flex-direction: row;
  padding: 1px 0;
`;
