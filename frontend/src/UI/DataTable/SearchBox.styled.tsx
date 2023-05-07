import styled from 'styled-components';

export const StyledSearchBox = styled.div`
  display: flex;
  flex-direction: row;
  font-size: 14px;
  padding: 5px 10px;
  border: 1px solid ${(props) => props.theme.colors.datatable.searchBoxBorder};
  border-radius: 6px;
  margin: 0 0 12px 16px;
  max-width: 250px;

  & input {
    color: ${(props) => props.theme.colors.text};
    font-size: 14px;
    width: 245px;
    height: 22px;
    margin-top: 3px;
    border: 0;
    outline: none;
    background: transparent url('/images/${(props) => props.theme.images.searchIcon}') no-repeat right center;
  }

  & input:not(:placeholder-shown) {
    background: transparent;
  }

  & input::-webkit-search-cancel-button {
    -webkit-appearance: none;
    width: 20px;
    height: 20px;
    right: 0;
    background: url('/images/${(props) => props.theme.images.searchCancel}') no-repeat right center;
  }
`;
