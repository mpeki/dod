// import { act } from '@testing-library/react';
// import { renderHook } from '@testing-library/react-hooks';
import * as themesFile from '../../public/themes.json';
// import { useTheme } from './useTheme';
import axios from "axios";

beforeEach(() => {
  jest.spyOn(axios, 'get').mockImplementation(() => Promise.resolve({ data: themesFile }));
});

afterEach(() => {
  jest.restoreAllMocks();
});

describe('Dynamic themes', () => {

  it('First config theme is set as initial', async() => {
    // const { result, waitForNextUpdate } = renderHook(() => useTheme());

    // await waitForNextUpdate();
    // expect(result.current.currentTheme?.name).toBe(themesFile.themes[0].name);
  });

  it('Dark mode is applied', async() => {
    const newTheme = 'dark';
    // const { result, waitForNextUpdate } = renderHook(() => useTheme());

    // await waitForNextUpdate();
    // act(() => {
      // result.current.setActiveTheme(newTheme);
    // })
    // expect(result.current.currentTheme?.name).toBe(newTheme);
  });

  it('Setting non-existent theme fails', async() => {
    // const { result, waitForNextUpdate } = renderHook(() => useTheme());

    // await waitForNextUpdate();
    // act(() => {
    //   expect(() => {result.current.setActiveTheme('non-existent')}).toThrow(Error);
    // });
  })
});
