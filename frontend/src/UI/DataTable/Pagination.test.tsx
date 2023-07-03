import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';
import { ThemeProvider } from 'styled-components';
import * as themeFile from '../../../public/themes.json';
import Pagination from './Pagination';

const setup = (currentPage: number, rowsPerPage: number, totalRecords: number) =>
  render(
    <ThemeProvider theme={themeFile.themes[0]}>
      <Pagination
        data-testid="pagination"
        currentPage={currentPage}
        rowsPerPage={rowsPerPage}
        totalRecords={totalRecords}
        onPageChange={jest.fn()}
      />
    </ThemeProvider>
  );

describe('Pagination component', () => {
/*
  it('renders regular version when limit is not exceeded', () => {
    const { getByTestId } = setup(2, 1, 11);
    for (let pg = 1; pg <= 11; pg++) {
      expect(getByTestId(`pg${pg}`)).toHaveTextContent(`${pg}`);
    }
  });

  it('renders truncated version with dropdown before last page when limit is exceeded', () => {
    const { getByTestId } = setup(4, 1, 12);
    for (let pg = 1; pg <= 10; pg++) {
      expect(getByTestId(`pg${pg}`)).toHaveTextContent(`${pg}`);
    }
    expect(getByTestId('pg12')).toHaveTextContent('12');

    userEvent.click(getByTestId('...before-last'));
    expect(getByTestId('pg11')).toHaveTextContent('11');
  });

  it('renders truncated version with dropdown after first page when limit is exceeded', () => {
    const { getByTestId } = setup(11, 1, 12);
    expect(getByTestId('pg1')).toHaveTextContent('1');

    userEvent.click(getByTestId('...after-first'));
    expect(getByTestId('pg2')).toHaveTextContent('2');

    for (let pg = 3; pg <= 12; pg++) {
      expect(getByTestId(`pg${pg}`)).toHaveTextContent(`${pg}`);
    }
  });

  it('renders truncated version with two dropdowns when limit is exceeded', () => {
    const { getByTestId } = setup(11, 1, 21);
    expect(getByTestId('pg1')).toHaveTextContent('1');

    userEvent.click(getByTestId('...before-current'));
    for (let pg = 2; pg <= 9; pg++) {
      expect(getByTestId(`pg${pg}`)).toHaveTextContent(`${pg}`);
    }

    for (let pg = 10; pg <= 12; pg++) {
      expect(getByTestId(`pg${pg}`)).toHaveTextContent(`${pg}`);
    }

    userEvent.click(getByTestId('...after-current'));
    for (let pg = 13; pg <= 20; pg++) {
      expect(getByTestId(`pg${pg}`)).toHaveTextContent(`${pg}`);
    }
    expect(getByTestId('pg21')).toHaveTextContent('21');
  });
*/
});
