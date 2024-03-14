interface IProps {
  height?: string;
  width?: string;
}
export const Spacer = ({ height, width } : IProps) => (
  <div style={{ height: height || '1em', width: width || '1em' }} />
);