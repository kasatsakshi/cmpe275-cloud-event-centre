import React, { useState, handleClick } from 'react';
import EventNavbar from './EventNavbar';
import './SignUpForum.css';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import styled from 'styled-components';

const Input = styled.textarea`
  width: 100%;
  height: 60%;
  margin: 10px 0;
`;

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 500,
  height: 200,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

function SignUpForum() {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [question, setQuestion] = useState('');
  // const [messageBoxOpen, setMessageBoxOpen] = React.useState(false);
  return (
    <div>
      <EventNavbar />
      <div className='forum-container'>
        <h1 className="forum-title">Signup Forum for </h1>
        <button className='forum-buttons' onClick={handleOpen}>Ask a question</button>
        <Modal
          open={open}
          onClose={handleClose}
          aria-describedby="modal-modal-description"
        >
          <Box sx={style}>
            <Input autoFocus placeholder="Add your question here" onChange={(e) => { setQuestion(e.target.value) }} />
            <button className='forum-buttons' onClick={handleClick}>Post Question</button>
          </Box>
        </Modal>
      </div>
    </div>
  )
}

export default SignUpForum