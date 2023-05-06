import styled from 'styled-components';

export const StyledBaseTraitItem = styled.div`
  color: ${(props) => props.theme.colors.text};
  .MuiListItem-padding {
    padding-top: 0;
    padding-bottom: 0;
  }
  .MuiListItemText-root {
    padding-top: 0;
    padding-bottom: 0;
  }
  display: flex;
  flex-direction: row;
  padding: 1px 0;
`;

