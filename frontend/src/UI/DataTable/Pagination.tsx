import { IconButton } from '@mui/material';
import React, { FC, useState } from 'react';
import {
  NextPageIcon,
  PaginationDropdown,
  PaginationDropdownItem,
  PrevPageIcon,
  StyledPagination,
  StyledPaginationItem,
} from './Pagination.styled';

export interface PaginationProps {
  currentPage: number;
  rowsPerPage: number;
  totalRecords: number;
  onPageChange: (newPage: number) => void;
}

const Pagination: FC<PaginationProps> = ({ currentPage, rowsPerPage, totalRecords, onPageChange }: PaginationProps) => {
  const PAGES_LIMIT = 10;
  const lastPage = Math.ceil(totalRecords / rowsPerPage);

  const prevItem = (
    <StyledPaginationItem className="prev">
      <IconButton
        size="medium"
        onClick={() => onPageChange(currentPage - 1)}
        style={{ backgroundColor: 'transparent', boxShadow: 'none' }}
      >
        <PrevPageIcon className={currentPage === 1 ? 'disabled' : ''} />
      </IconButton>
    </StyledPaginationItem>
  );

  const nextItem = (
    <StyledPaginationItem className="next">
      <IconButton
        size="medium"
        onClick={() => onPageChange(currentPage + 1)}
        style={{ backgroundColor: 'transparent', boxShadow: 'none' }}
      >
        <NextPageIcon className={currentPage === lastPage ? 'disabled' : ''} />
      </IconButton>
    </StyledPaginationItem>
  );

  return (
    <nav>
      <StyledPagination>
        {prevItem}
        {lastPage <= PAGES_LIMIT && (
          <GeneratedPages fromPage={1} toPage={lastPage} currentPage={currentPage} onClick={onPageChange}></GeneratedPages>
        )}
        {lastPage > PAGES_LIMIT && currentPage < PAGES_LIMIT && (
          <>
            <GeneratedPages fromPage={1} toPage={PAGES_LIMIT} currentPage={currentPage} onClick={onPageChange}></GeneratedPages>
            <PagesDropDown id={'before-last'} fromPage={PAGES_LIMIT + 1} toPage={lastPage - 1} onPageChange={onPageChange}></PagesDropDown>
            <GeneratedPages fromPage={lastPage} toPage={lastPage} currentPage={currentPage} onClick={onPageChange}></GeneratedPages>
          </>
        )}
        {lastPage > PAGES_LIMIT && currentPage >= PAGES_LIMIT && (lastPage - currentPage <= PAGES_LIMIT - 1 ? (
          <>
            <GeneratedPages fromPage={1} toPage={1} currentPage={currentPage} onClick={onPageChange}></GeneratedPages>
            <PagesDropDown id={'after-first'} fromPage={2} toPage={lastPage - PAGES_LIMIT} onPageChange={onPageChange}></PagesDropDown>
            <GeneratedPages fromPage={lastPage - PAGES_LIMIT + 1} toPage={lastPage} currentPage={currentPage} onClick={onPageChange}></GeneratedPages>
          </>
        ) : (
          <>
            <GeneratedPages fromPage={1} toPage={1} currentPage={currentPage} onClick={onPageChange}></GeneratedPages>
            <PagesDropDown id={'before-current'} fromPage={2} toPage={currentPage - 2} onPageChange={onPageChange}></PagesDropDown>
            <GeneratedPages fromPage={currentPage - 1} toPage={currentPage + 1} currentPage={currentPage} onClick={onPageChange}></GeneratedPages>
            <PagesDropDown id={'after-current'} fromPage={currentPage + 2} toPage={lastPage - 1} onPageChange={onPageChange}></PagesDropDown>
            <GeneratedPages fromPage={lastPage} toPage={lastPage} currentPage={currentPage} onClick={onPageChange}></GeneratedPages>
          </>
        ))}
        {nextItem}
      </StyledPagination>
    </nav>
  );
};

export default Pagination;

const GeneratedPages: FC<{fromPage: number; toPage: number; currentPage: number; onClick: (pg: number) => void}> = (props): JSX.Element => {
  return (
    <>
      {Array(props.toPage - props.fromPage + 1).fill(1)
        .map((v, index) => (
          <StyledPaginationItem
            key={index + props.fromPage}
            data-testid={'pg' + (index + props.fromPage)}
            onClick={() => props.onClick(index + props.fromPage)}
            className={props.currentPage === index + props.fromPage ? 'selected' : ''}
          >
            {index + props.fromPage}
          </StyledPaginationItem>
        ))
      }
    </>
  );
}

const PagesDropDown: FC<{id: string; fromPage: number; toPage: number; onPageChange: (pg: number) => void}> = (props): JSX.Element => {
  const [dropdownAnchorEl, setdropdownAnchorEl] = useState<HTMLLIElement | null>(null);

  const handleDropdownPage = (event: React.MouseEvent<HTMLLIElement>) => {
    setdropdownAnchorEl(event.currentTarget);
  };

  const handleDropdownClose = () => {
    setdropdownAnchorEl(null);
  };

  const handlePageClick = (event: React.MouseEvent<HTMLLIElement>) => {
    const pageValue = (event.target as HTMLLIElement).innerText;

    handleDropdownClose();
    props.onPageChange(+pageValue);
  };

  return (
    <StyledPaginationItem key={'...' + props.id} className="more-pages">
      <span data-testid={'...' + props.id} onClick={handleDropdownPage}>
        ...
      </span>
      <PaginationDropdown
        id={props.id}
        anchorEl={dropdownAnchorEl}
        keepMounted
        open={Boolean(dropdownAnchorEl)}
        onClose={handleDropdownClose}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
        transformOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        PaperProps={{
          style: {
            maxHeight: 218,
            width: '5ch',
          },
        }}
      >
        {Array(props.toPage - props.fromPage + 1).fill(1)
          .map((v, index) => (
            <PaginationDropdownItem key={index + props.fromPage} data-testid={'pg' + (index + props.fromPage)} onClick={handlePageClick}>
              {index + props.fromPage}
            </PaginationDropdownItem>
          ))
        }
      </PaginationDropdown>
    </StyledPaginationItem>
  );
};
